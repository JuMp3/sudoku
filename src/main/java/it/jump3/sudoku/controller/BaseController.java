package it.jump3.sudoku.controller;

import it.jump3.sudoku.web.FlashMessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected MessageSource messageSource;

    private static final String FLASH_MESSAGE_LIST = "flashMessageList";

    protected void addFlashMessage(final RedirectAttributes redirectAttributes, final Locale locale,
                                   final FlashMessageTypeEnum flashMessageType, final String key, final Object... param) {

        String message = messageSource.getMessage(key, param, key, locale);
        log.info(message);

        List<String> flashMessageList = new ArrayList<String>();
        Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();
        for (Map.Entry<String, ?> entry : flashAttributes.entrySet()) {
            String keyz = entry.getKey();
            if (FLASH_MESSAGE_LIST.equalsIgnoreCase(keyz)) {
                flashMessageList = (List<String>) entry.getValue();
            }
        }

        String flashMessage = String.format("%d§%s§%s", flashMessageList.size() + 1, flashMessageType.getValue(),
                message);
        flashMessageList.add(flashMessage);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE_LIST, flashMessageList);
    }

    protected String getMsgFromBundle(String key, Locale locale, Object... param) {
        return messageSource.getMessage(key, param, locale);
    }
}
