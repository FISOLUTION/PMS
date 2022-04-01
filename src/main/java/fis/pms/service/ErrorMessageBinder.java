package fis.pms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorMessageBinder {

    private final MessageSource messageSource;

    public String bindMessage(String[] codes, Object[] args){
        String message = null;
        for(int i = 0; i < codes.length; i++){
            try {
                message = messageSource.getMessage(codes[i], args, null);
                if (message != null) break;
            } catch (Exception exception) {
                continue;
            }
        }
        if(message != null){
            return message;
        }
        else {
            return "예기치않은 오류";
        }
    }

    public String bindMessage(String code, Object[] args){
        String message = null;
        return null;
        //return Optional.ofNullable(messageSource.getMessage(code, args, null));
    }
}
