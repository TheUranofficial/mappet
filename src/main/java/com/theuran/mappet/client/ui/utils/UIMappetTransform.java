package com.theuran.mappet.client.ui.utils;

import mchorse.bbs_mod.data.types.ListType;
import mchorse.bbs_mod.graphics.window.Window;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.Keys;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Axis;
import mchorse.bbs_mod.utils.colors.Colors;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;

public class UIMappetTransform extends UIElement
{
    public UITrackpad sx;
    public UITrackpad sy;
    public UITrackpad sz;
    public UITrackpad s2x;
    public UITrackpad s2y;
    public UITrackpad s2z;
    
    protected UIIcon iconS;
    protected UIIcon iconS2;

    protected UIElement scaleRow;

    private boolean uniformDrag;
    private boolean uniformScale;

    public UIMappetTransform()
    {
        super();

        IKey raw = IKey.constant("%s (%s)");

        this.sx = new UITrackpad((value) ->
        {
            this.internalSetS(value, Axis.X);
            this.syncScale(value);
        }).disableCanceling();
        this.sx.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_X));
        this.sx.textbox.setColor(Colors.RED);
        this.sy = new UITrackpad((value) ->
        {
            this.internalSetS(value, Axis.Y);
            this.syncScale(value);
        }).disableCanceling();
        this.sy.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_Y));
        this.sy.textbox.setColor(Colors.GREEN);
        this.sz = new UITrackpad((value) ->
        {
            this.internalSetS(value, Axis.Z);
            this.syncScale(value);
        }).disableCanceling();
        this.sz.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_Z));
        this.sz.textbox.setColor(Colors.BLUE);

        /// ///////////////////////////

        this.s2x = new UITrackpad((value) -> {
            this.internalSetS2(value, Axis.X);
            this.syncScale2(value);
        }).disableCanceling();
        this.s2x.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_X));
        this.s2x.textbox.setColor(Colors.RED);

        this.s2y = new UITrackpad((value) -> {
            this.internalSetS2(value, Axis.Y);
            this.syncScale2(value);
        }).disableCanceling();
        this.s2y.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_Y));
        this.s2y.textbox.setColor(Colors.GREEN);

        this.s2z = new UITrackpad((value) -> {
            this.internalSetS2(value, Axis.Z);
            this.syncScale2(value);
        }).disableCanceling();
        this.s2z.onlyNumbers().tooltip(raw.format(UIKeys.TRANSFORMS_SCALE, UIKeys.GENERAL_Z));
        this.s2z.textbox.setColor(Colors.BLUE);

        this.w(1F).column().stretch().vertical();

        this.iconS = new UIIcon(Icons.SCALE, (b) -> this.toggleUniformScale());
        this.iconS.tooltip(UIKeys.TRANSFORMS_UNIFORM_SCALE);

        this.iconS2 = new UIIcon(Icons.SCALE, (b) -> this.toggleUniformScale());
        this.iconS.tooltip(UIKeys.TRANSFORMS_UNIFORM_SCALE);

        this.add(this.scaleRow = UI.row(this.iconS, this.sx, this.sy, this.sz));
        this.add(this.scaleRow = UI.row(this.iconS2, this.s2x, this.s2y, this.s2z));

        this.context((menu) ->
        {
            ListType transforms = Window.getClipboardList();

            if (transforms != null && transforms.size() < 9)
            {
                transforms = null;
            }

            menu.autoKeys().action(Icons.COPY, UIKeys.TRANSFORMS_CONTEXT_COPY, this::copyTransformations);

            if (transforms != null)
            {
                final ListType innerList = transforms;

                menu.action(Icons.PASTE, UIKeys.TRANSFORMS_CONTEXT_PASTE, () -> this.pasteAll(innerList));
                menu.action(Icons.MAXIMIZE, UIKeys.TRANSFORMS_CONTEXT_PASTE_SCALE, () -> this.pasteScale(this.getVector(innerList, 3)));
                menu.action(Icons.REFRESH, UIKeys.TRANSFORMS_CONTEXT_PASTE_ROTATION, () -> this.pasteScale2(this.getVector(innerList, 6)));
            }

            menu.action(Icons.CLOSE, UIKeys.TRANSFORMS_CONTEXT_RESET, this::reset);
        });

        this.wh(190, 70);

        this.keys().register(Keys.COPY, this::copyTransformations).inside().label(UIKeys.TRANSFORMS_CONTEXT_COPY);
        this.keys().register(Keys.PASTE, () ->
        {
            ListType transforms = Window.getClipboardList();

            if (transforms != null && transforms.size() < 9)
            {
                transforms = null;
            }

            if (transforms != null)
            {
                this.pasteAll(transforms);
            }
        }).inside().label(UIKeys.TRANSFORMS_CONTEXT_PASTE);
    }

    protected void toggleUniformScale()
    {
        this.uniformScale = !this.uniformScale;

        this.scaleRow.removeAll();

        if (this.uniformScale)
        {
            this.scaleRow.add(this.iconS, this.sx);
        }
        else
        {
            this.scaleRow.add(this.iconS, this.sx, this.sy, this.sz);
        }

        UIElement parentContainer = this.getParentContainer();

        if (parentContainer != null)
        {
            parentContainer.resize();
        }
    }

    protected boolean isUniformScale()
    {
        return this.uniformDrag || Window.isKeyPressed(GLFW.GLFW_KEY_SPACE);
    }

    private void syncScale(double value)
    {
        if (this.isUniformScale())
        {
            this.fillS(value, value, value);
            this.setS(null, value, value, value);
        }
    }

    private void syncScale2(double value)
    {
        if (this.isUniformScale())
        {
            this.fillS2(value, value, value);
            this.setS2(null, value, value, value);
        }
    }

    public void fillSetS(double x, double y, double z)
    {
        this.fillS(x, y, z);
        this.setS(null, x, y, z);
    }

    public void fillSetS2(double x, double y, double z)
    {
        this.fillS2(x, y, z);
        this.setS2(null, x, y, z);
    }


    public void fillS(double x, double y, double z)
    {
        this.sx.setValue(x);
        this.sy.setValue(y);
        this.sz.setValue(z);
    }

    public void fillS2(double x, double y, double z)
    {
        this.s2x.setValue(x);
        this.s2y.setValue(y);
        this.s2z.setValue(z);
    }

    protected void internalSetS(double x, Axis axis)
    {
        try
        {
            if (this.uniformScale && axis == Axis.X)
            {
                this.setS(axis, x, x, x);
                this.sy.setValue(x);
                this.sz.setValue(x);

                return;
            }

            this.setS(axis,
                    axis == Axis.X ? x : this.sx.getValue(),
                    axis == Axis.Y ? x : this.sy.getValue(),
                    axis == Axis.Z ? x : this.sz.getValue()
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void internalSetS2(double x, Axis axis)
    {
        try
        {
            this.setS2(axis,
                    axis == Axis.X ? x : this.s2x.getValue(),
                    axis == Axis.Y ? x : this.s2y.getValue(),
                    axis == Axis.Z ? x : this.s2z.getValue()
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setS(Axis axis, double x, double y, double z) {

    };

    public void setS2(Axis axis, double x, double y, double z) {

    };

    private void copyTransformations()
    {
        ListType list = new ListType();

        list.addDouble(this.sx.getValue());
        list.addDouble(this.sy.getValue());
        list.addDouble(this.sz.getValue());
        list.addDouble(this.s2x.getValue());
        list.addDouble(this.s2y.getValue());
        list.addDouble(this.s2z.getValue());

        Window.setClipboard(list);
    }

    public void pasteAll(ListType list)
    {
        this.pasteScale(this.getVector(list, 3));
        this.pasteScale2(this.getVector(list, 6));
    }

    public void pasteScale(Vector3d scale)
    {
        this.setS(null, scale.x, scale.y, scale.z);
    }

    public void pasteScale2(Vector3d scale)
    {
        this.setS2(null, scale.x, scale.y, scale.z);
    }

    private Vector3d getVector(ListType list, int offset)
    {
        Vector3d result = new Vector3d();

        if (list.get(offset).isNumeric() && list.get(offset + 1).isNumeric() && list.get(offset + 2).isNumeric())
        {
            result.x = list.get(offset).asNumeric().doubleValue();
            result.y = list.get(offset + 1).asNumeric().doubleValue();
            result.z = list.get(offset + 2).asNumeric().doubleValue();
        }

        if (offset == 0)
        {
            result.x *= Window.isShiftPressed() ? -1 : 1;
        }

        if (offset >= 6)
        {
            result.y *= Window.isShiftPressed() ? -1 : 1;
            result.z *= Window.isShiftPressed() ? -1 : 1;
        }

        return result;
    }

    protected void reset()
    {
        this.fillSetS(1, 1, 1);
        this.fillSetS2(0, 0, 0);
    }

    @Override
    protected boolean subMouseClicked(UIContext context)
    {
        if (this.sx.area.isInside(context) || this.sy.area.isInside(context) || this.sz.area.isInside(context))
        {
            if (context.mouseButton == 1 && (this.sx.isDragging() || this.sy.isDragging() || this.sz.isDragging()))
            {
                this.uniformDrag = true;

                return true;
            }
        }

        return super.subMouseClicked(context);
    }

    @Override
    protected boolean subMouseReleased(UIContext context)
    {
        if (context.mouseButton == 1)
        {
            this.uniformDrag = false;
        }

        return super.subMouseReleased(context);
    }

    @Override
    protected boolean subKeyPressed(UIContext context)
    {
        if (this.sx.isDragging() || this.sy.isDragging() || this.sz.isDragging())
        {
            if (context.isHeld(GLFW.GLFW_KEY_SPACE))
            {
                return true;
            }
        }

        return super.subKeyPressed(context);
    }
}
