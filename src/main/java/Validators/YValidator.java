package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class YValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        double y;
        try {
            y = (Double) o;
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage("В поле y должно быть число!"));
        }
        if (y <= -3 || y >= 5) {
            throw new ValidatorException(new FacesMessage("Значение должно быть в диапозоне (-3...5)"));
        }
    }
}
