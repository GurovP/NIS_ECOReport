package eco.nis.models.entities;

public enum ProjectPhases {

    Feasibility(1),
    Planning(2),
    Implementation(3),
    Closure(4),
    Pause(5);

    private int code;

    ProjectPhases(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public static ProjectPhases fromCode(int code) {

        for(ProjectPhases cs: values())
            if(cs.code == code)
                return cs;

        return ProjectPhases.Planning;
    }
}