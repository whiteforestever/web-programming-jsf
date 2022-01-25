
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FormHit implements Serializable {
    private double x = 0;
    private double y = 0;
    private double r = 3;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {return y;}

    public void setY(double y) {
        this.y = y;
    }

    public double getR(){return r;}

    public void setR(double r){this.r = r;}

    public boolean validateValues(){

        if(!(getX()==-2||getX()==-1.5||getX()==-1||getX()==-0.5||getX()==0||getX()==0.5||getX()==1||getX()==1.5||getX()==2)){
            return false;
        }

        if(!(-2.999<=getY() && getY()<=4.9999)){
            return false;
        }

        if(!(getR()==1||getR()==2||getR()==3||getR()==4||getR()==5)){
            return false;
        }

        return true;
    }

    public void setDefaultValues(){
        setX(0.0);
        setY(0.0);
        setR(3.0);
    }
}
