package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class RValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        double r;
        try {
            r = (Double) o;
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage("В поле r должно быть число!"));
        }
        if (r < 1 || r > 5) {
            throw new ValidatorException(new FacesMessage("Значение должно быть в диапозоне 1...5"));
        }
    }
}
