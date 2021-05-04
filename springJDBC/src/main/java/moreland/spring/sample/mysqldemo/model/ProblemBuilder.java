package moreland.spring.sample.mysqldemo.model;

public class ProblemBuilder {
    private String type;
    private String title;
    private String instance;
    private Number status;
    private String detail;

    private final String STATUSES = "https://httpstatuses.com/";

    public ProblemBuilder() {
        super();

        type = STATUSES + 500;
        title = "unknown";
        instance = "about:blank";
        status = 500;
        detail = "Unknown error occurred";
    }

    public ProblemBuilder withStatus(Number status) {
        this.status = status;
        this.type = STATUSES + status.toString();
        return this;
    }
    public ProblemBuilder withTitle(String title) {
        this.title = title;
        return this;
    }
    public ProblemBuilder withInstance(String instance) {
        this.instance = instance;
        return this;
    }
    public ProblemBuilder withDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public Problem build() {
        var problem = new Problem();
        problem.setType(type);
        problem.setTitle(title);
        problem.setStatus(status);
        problem.setInstance(instance);
        problem.setDetail(detail);
        return problem;
    }
}
