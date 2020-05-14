package yves.leung.com.common.validator;

import org.apache.commons.lang3.StringUtils;
import yves.leung.com.common.annotation.IsMobile;
import yves.leung.com.common.entity.RegexpConstant;
import yves.leung.com.common.utils.LeungUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return LeungUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}