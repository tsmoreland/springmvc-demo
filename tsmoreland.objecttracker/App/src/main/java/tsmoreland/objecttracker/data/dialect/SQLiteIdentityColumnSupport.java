//
// see https://github.com/eugenp/tutorials/blob/950bbadc353bdca114befc98cf4a18476352220e/spring-data-rest/src/main/java/com/baeldung/books/dialect/SQLiteDialect.java
//

package tsmoreland.objecttracker.data.dialect;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) throws MappingException {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) throws MappingException {
        return "integer";
    }
}
