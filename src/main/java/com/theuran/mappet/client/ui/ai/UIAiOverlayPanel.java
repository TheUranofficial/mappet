package com.theuran.mappet.client.ui.ai;

import com.theuran.mappet.client.ai.AiMain;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.forms.editors.forms.UIForm;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;
import mchorse.bbs_mod.ui.utils.UI;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.theuran.mappet.client.ai.AiMain.getAIResponse;

public class UIAiOverlayPanel extends UIOverlayPanel {
    private final UIText scriptName;
    private final UIText aiText;
    private final UITextbox questionInput;
    private final UIButton questionSend;
    private final UIMascotPanel mascotPanel;
    private final UIScrollView scrollView;


    private String question = "";

    private final long MAX_DELAY = 2;

    private long delay = MAX_DELAY;

    private final List<Character> chars = new ArrayList<>();

    public UIAiOverlayPanel(String id, String content) {
        super(UIMappetKeys.AI_TITLE);

        AiMain.HISTORY.clear();

        String script = "```js\n"+content+"\n```";

        Thread.startVirtualThread(() -> {
            getAIResponse("Вот мой Js скрипт для мода майнкрафт 1 12 2 mappet на javet, пока пользователь не попросит не взаимодействуй с этим скриптом\n" + script, true);
        });

        ///      НАЗВАНИЯ СКРИПТА
        this.scriptName = new UIText();
        this.scriptName.text("§7" + UIMappetKeys.AI_SCRIPT + id);
        this.scriptName.x(5);
        this.scriptName.wh(1f, 1f);
        this.scriptName.relative(this.content);

        ///      ТЕКСТ
        this.aiText = new UIText();
        this.aiText.text("");
        this.aiText.w(1f, -60).h(1f, -80);
        this.aiText.relative(this.content);

        ///      ВВОД ТЕКСТА
        this.questionInput = new UITextbox((question) -> {
            this.question = question;
        });
        this.questionInput.w(1f, -60).h(20);
        this.questionInput.x(5).y(1f, -30);
        this.questionInput.placeholder(UIMappetKeys.AI_REQUEST_PLACEHOLDER);
        this.questionInput.relative(this.content);

        ///      КНОПКА
        this.questionSend = new UIButton(L10n.lang(">"), (b) -> {
            for (char c : ("§9" + MinecraftClient.getInstance().player.getGameProfile().getName() + "§f: " + question + "\n").toCharArray()) {
                this.chars.add(c);
            }

            this.handleAnswer();
            questionInput.setText("");
        });
        this.questionSend.w(40).h(20);
        this.questionSend.x(1f, -45).y(1f, -30);
        this.questionSend.relative(this.content);

        ///      ПАНЕЛЬ КОРОБКИ
        this.mascotPanel = new UIMascotPanel();
        this.mascotPanel.relative(this.content);
        this.mascotPanel.w(1f, -200).h(1f, -500);
        this.mascotPanel.x(-200).y(-20);

        ///      СКРОЛ ТЕКСТА
        this.scrollView = UI.scrollView(0, 0, new UIElement[0]);
        this.scrollView.full(this);
        this.scrollView.add(this.aiText);
        this.scrollView.x(10).y(40);
        this.scrollView.w(1f, -40).h(1f, -80);

        this.add(this.scrollView, this.questionInput, this.questionSend, /*this.mascotPanel,*/ this.scriptName);
    }

    private void handleAnswer() {
        Thread.startVirtualThread(() -> {
            String answer = getAIResponse(this.question, true);

            String answerFormatted = answerFormatted(answer);

            extractJsBlocks(answerFormatted);


            String answerScript = extractPart(answer);

            for (char c : (answerFormatted + "\n\n\n" + answerScript).toCharArray()) {
                this.chars.add(c);
            }
        });
    }

    public List<String> extractJsBlocks(String text) {
        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("```js\\s*([\\s\\S]*?)\\s*```");
        Matcher matcher = pattern.matcher(text);

        int lastEnd = 0;

        while (matcher.find()) {
            // Обычный текст до JS блока
            String before = text.substring(lastEnd, matcher.start()).trim();
            if (!before.isEmpty()) {
                result.add(before);
            }

            // Содержимое JS блока
            String code = matcher.group(1).trim();
            result.add(code);

            lastEnd = matcher.end();
        }

        // Остаток текста после последнего блока
        String after = text.substring(lastEnd).trim();
        if (!after.isEmpty()) {
            result.add(after);
        }

        return result;
    }

    private String answerFormatted(String answer) {
        return "\n§cMP Pilot§f: " + answer.replace("<think>", "§7§o").replace("</think>", "§r\n");
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

