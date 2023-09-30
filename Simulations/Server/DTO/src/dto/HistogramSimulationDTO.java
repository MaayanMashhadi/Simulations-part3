package dto;

import java.util.Map;

public class HistogramSimulationDTO {
    private Map<Object, Integer> histogram;
    private int consistency;
    private Float average;

    public HistogramSimulationDTO(Map<Object, Integer> histogram, int consistency) {
        this.histogram = histogram;
        this.consistency = consistency;
        float amountOfProperty = 0.0F;
        int population = 0;
        for(Map.Entry<Object, Integer> entry : histogram.entrySet()){
            if(entry.getKey() instanceof Float){
                amountOfProperty +=(Float) entry.getKey() * entry.getValue();
                population += entry.getValue();
            }

        }
        average = amountOfProperty/population;

    }

    public Float getAverage() {
        return average;
    }

    public Map<Object, Integer> getHistogram() {
        return histogram;
    }

    public int getConsistency() {
        return consistency;
    }
}
