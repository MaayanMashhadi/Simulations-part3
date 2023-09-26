package logic.dto;

public class TerminateConditionDTO {
    private final Integer seconds;
    private final Integer ticks;
    private final String byUser;

    public Integer getSeconds() {
        return seconds;
    }

    public Integer getTicks() {
        return ticks;
    }

    public String getByUser() {
        return byUser;
    }

    public TerminateConditionDTO(Integer seconds, Integer ticks) {
        this.seconds = seconds;
        this.ticks = ticks;
        byUser = "End by user";
    }


}
