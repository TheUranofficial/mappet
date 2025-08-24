package com.theuran.mappet.resources.packs;

import com.theuran.mappet.Mappet;
import mchorse.bbs_mod.resources.ISourcePack;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.resources.packs.ExternalAssetsSourcePack;
import mchorse.bbs_mod.utils.DataPath;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MappetInternalAssetsPack implements ISourcePack {
    private String prefix;
    private String internalPrefix;
    private Class clazz;
    private boolean isForge;
    private List<String> zipCache;

    public MappetInternalAssetsPack() {
        this("mappet", "assets/mappet", MappetInternalAssetsPack.class);
    }

    public MappetInternalAssetsPack(String prefix, String internalPrefix, Class<?> clazz) {
        this.zipCache = new ArrayList<>();
        this.prefix = prefix;
        this.internalPrefix = internalPrefix;
        this.clazz = clazz;

        try {
            Class.forName("net.minecraftforge.common.MinecraftForge");
            this.isForge = true;
        } catch (ClassNotFoundException e) {
        }
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public boolean hasAsset(Link link) {
        ClassLoader loader = this.clazz.getClassLoader();

        return loader.getResource(this.internalPrefix + "/" + link.path) != null;
    }

    @Override
    public InputStream getAsset(Link link) {
        return this.clazz.getClassLoader().getResourceAsStream(this.internalPrefix + "/" + link.path);
    }

    @Override
    public File getFile(Link link) {
        return null;
    }

    @Override
    public Link getLink(File file) {
        return null;
    }

    @Override
    public void getLinksFromPath(Collection<Link> links, Link link, boolean recursive) {
        if (this.isForge) {
            this.findLinksForJar(links, link, recursive);
        } else {
            URL url = this.clazz.getProtectionDomain().getCodeSource().getLocation();

            try {
                File file = Paths.get(url.toURI()).toFile();

                if (file.isDirectory()) {
                    this.getLinksFromFolder(this.getResourcesFolder(file), link, links, recursive);
                } else if (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
                    this.getLinksFromZipFile(file, link, links, recursive);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.findLinksForJar(links, link, recursive);
            }
        }
    }

    private void findLinksForJar(Collection<Link> links, Link link, boolean recursive) {
        FabricLoader.getInstance().getModContainer(Mappet.MOD_ID).ifPresent(mod -> {
            Path jarPath = mod.getOrigin().getPaths().stream().findFirst().orElse(null);

            if (jarPath != null) {
                File jarFile = jarPath.toFile();

                if (jarFile.isFile() && jarFile.getName().endsWith(".jar")) {
                    this.getLinksFromZipFile(jarFile, link, links, recursive);
                }
            }
        });
    }

    private File getResourcesFolder(File file) {
        if (!new File(file, this.internalPrefix).exists()) {
            for (File subFile : file.getParentFile().listFiles()) {
                if (new File(subFile, this.internalPrefix).exists()) {
                    return subFile;
                }
            }
        }
        return file;
    }

    private void getLinksFromFolder(File folder, Link link, Collection<Link> links, boolean recursive) {
        File file = new File(folder, this.internalPrefix + "/" + link.path);

        ExternalAssetsSourcePack.getLinksFromPathRecursively(file, links, link, link.path, recursive ? 9999 : 1);
    }

    private void getLinksFromZipFile(File file, Link link, Collection<Link> links, boolean recursive) {
        try (ZipFile zipFile = new ZipFile(file)) {
            if (this.zipCache.isEmpty()) {
                Enumeration<? extends ZipEntry> it = zipFile.entries();

                while (it.hasMoreElements()) {
                    String name = it.nextElement().getName();

                    if (name.startsWith(this.internalPrefix)) {
                        this.zipCache.add(name);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.handleLinksFromZipFile(link, links, recursive);
    }

    private void handleLinksFromZipFile(Link link, Collection<Link> links, boolean recursive) {
        DataPath assetsPath = new DataPath(this.internalPrefix + "/");

        for (String zipName : this.zipCache) {
            DataPath zipPath = new DataPath(zipName);
            DataPath fullPath = new DataPath(assetsPath + "/" + link.path);

            if (!zipPath.equals(fullPath) && zipPath.startsWith(fullPath)) {
                for (int i = 0; i < assetsPath.size(); ++i) {
                    zipPath.strings.remove(0);
                    fullPath.strings.remove(0);
                }

                if (recursive || zipPath.size() == fullPath.size() + 1) {
                    links.add(new Link(this.prefix, zipPath + (zipPath.folder ? "/" : "")));
                }
            }
        }
    }
}
