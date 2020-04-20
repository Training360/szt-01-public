package empapp;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EmployeeWithNameMatcher extends TypeSafeMatcher<Employee> {

    public static Matcher<Employee> employeeWithName(Matcher<String> matcher) {
        return new EmployeeWithNameMatcher(matcher);
    }

    private Matcher<String> matcher;

    public EmployeeWithNameMatcher(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Employee item) {
        return matcher.matches(item.getName());
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(" employee with name ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected void describeMismatchSafely(Employee item, Description mismatchDescription) {
        mismatchDescription
                .appendText(" employee with name ")
                .appendValue(item.getName());
    }
}
