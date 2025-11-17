package com.theuran.mappet.client.ui.ai;

import com.theuran.mappet.client.ai.AiMain;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UIModelRenderer;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;
import mchorse.bbs_mod.ui.utils.UI;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

import static com.theuran.mappet.client.ai.AiMain.getAIResponse;

public class UIAiOverlayPanel extends UIOverlayPanel {
    private final UIText aiText;
    private String question = "";

    private final long MAX_DELAY = 2;

    private long delay = MAX_DELAY;

    private final List<Character> chars = new ArrayList<>();

    public UIAiOverlayPanel(String id, String content) {
        super(UIMappetKeys.AI_TITLE);

        AiMain.history.clear();

        String script = "```js\n"+content+"\n```";

        Thread.startVirtualThread(() -> {
            getAIResponse("Вот мой Js скрипт для мода майнкрафт 1 12 2 mappet на javet, пока пользователь не попросит не взаимодействуй с этим скриптом\n" + script, true);
        });

        ///      НАЗВАНИЯ СКРИПТА
        UIText ScriptName = new UIText();
        ScriptName.text("§7" + UIMappetKeys.AI_SCRIPT + id);
        ScriptName.x(5).y(0);
        ScriptName.w(1f, 0).h(1f, 0);
        ScriptName.relative(this.content);

        ///      ТЕКСТ
        this.aiText = new UIText();
        this.aiText.text("");
//        this.aiText.text("a\n".repeat(1000));
        this.aiText.x(10);
        this.aiText.w(1f, -20).h(1f, -100);
        this.aiText.relative(this.content);

        ///      ВВОД ТЕКСТА
        UITextbox question_input = new UITextbox((g) -> {
            question = g;
        });
        question_input.w(1f, -60).h(1f, -650);
        question_input.x(10).y(640);
        question_input.placeholder(UIMappetKeys.AI_REQUEST_PLACEHOLDER);
        question_input.relative(this.content);

        ///      КНОПКА
        UIButton question_send = new UIButton(L10n.lang(">"), (b) -> {
            for (char c : ("§9" + MinecraftClient.getInstance().player.getGameProfile().getName() + "§f: " + question + "\n").toCharArray()) {
                this.chars.add(c);
            }

            Thread.startVirtualThread(() -> {
                String answer = getAIResponse(question, true);

                String answer_view = ("\n§cMP Pilot§f: " + answer.replace("<think>", "§7§o").replace("</think>", "§r\n") + "\n\n-----------------------------\n");
                String answer_script = extractPart(answer);

                for (char c : (answer_view + "\n\n\n" + answer_script).toCharArray()) {
                    this.chars.add(c);
                }
            });
            question_input.setText("");
        });
        question_send.w(1f, -350).h(1f, -650);
        question_send.x(340).y(640);
        question_send.relative(this.content);

        ///      ПАНЕЛЬ КОРОБКИ
        UIMascotPanel MascotPanel = new UIMascotPanel();
        MascotPanel.relative(this.content);
        MascotPanel.w(1f, -200).h(1f, -500);
        MascotPanel.x(-200).y(-20);

        ///      СКРОЛ ТЕКСТА
        UIScrollView scrollView = UI.scrollView(0, 0, new UIElement[0]);
        scrollView.full(this);
        scrollView.add(this.aiText);
        scrollView.w(1f, -30).h(1f, -80);
        scrollView.x(10).y(40);

        this.add(scrollView, question_input, question_send, MascotPanel, ScriptName);
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        if (this.chars != null && !this.chars.isEmpty()) {
            if (this.delay > 0) {
                this.delay--;
            } else {
                this.aiText.text(this.aiText.getText().get() + this.chars.getFirst());
                this.delay = MAX_DELAY;
                this.chars.removeFirst();
            }
        }
    }

    public static String extractPart(String text) {
        int start = text.indexOf("```js");
        int end = text.indexOf("```");
        if (start != -1 && end != -1 && end > start) {
            return text.substring(start, end + 2);
        }
        return "";
    }
}

