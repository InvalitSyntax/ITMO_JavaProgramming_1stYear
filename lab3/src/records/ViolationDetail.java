package records;

import enums.Violation;

public record ViolationDetail(Violation violation, int timeWhenDid) {}