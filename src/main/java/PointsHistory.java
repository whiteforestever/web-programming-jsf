import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class PointsHistory implements Serializable {
    @Inject
    DataBaseManager dataBaseManager;
    private List<PointsData> pointsDataList;

    @PostConstruct
    public void initializeHits() {
        pointsDataList = dataBaseManager.getHits();
    }

    @Inject FormHit formhit;
    public void clearAll(){
        formhit.setDefaultValues();
        dataBaseManager.deleteBase();
        pointsDataList.clear();
        addStoredHitsToCanvas();
        formhit.setDefaultValues();
    }

    public List<PointsData> getHitResultList() {
        Collections.reverse(pointsDataList);
        return pointsDataList;
    }

    public void addFromForm() {
        if(formhit.validateValues()){
            double x = formhit.getX();
            double y = formhit.getY();
            double r = formhit.getR();
            addHits(calculateHit(x,y,r));
        }
    }

    private PointsData calculateHit(double x, double y, double radius) {
        return new PointsData(x, y, radius, doesItHit(x, y, radius));
    }

    private boolean doesItHit(double x, double y, double radius) {
        if (-radius/2 <= x && x <= 0 && y >= 0 && y <= (x + radius/2))
            return true;
        if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(radius/2, 2) && x >= 0 && y >= 0)
            return true;
        return (x <= 0 && x >= -radius/2 && y >= -radius && y <= 0);
    }

    @Inject CanvasHit canvasHit;
    public void addFromChart() {
        if(formhit.getR()>=1.0 && formhit.getR()<=5.0){
            PointsData pointsData = calculateHit(canvasHit.getX(), canvasHit.getY(), canvasHit.getR());
            addHits(pointsData);
        }
    }

    private void addHits(PointsData hits) {
        if (dataBaseManager.addHits(hits)) {
            pointsDataList.add(hits);
            addHitsToCanvas(Collections.singletonList(hits));
        }
    }

    public void addStoredHitsToCanvas() {
        addHitsToCanvas(pointsDataList);
    }

    private void addHitsToCanvas(List<PointsData> hits) {
        String json = hits.stream()
            .map(hit -> "{" +
                " x: " + hit.getX() + "," +
                " y: " + hit.getY() + "," +
                " r: " + hit.getR() + "," +
                " doesHit: " + hit.isDoesHit() + " }"
            )
            .collect(Collectors.joining(", ", "[", "]"));
        PrimeFaces.current().executeScript("addHits(" + json + ")");
    }
}
