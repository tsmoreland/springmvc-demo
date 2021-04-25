package com.moreland.data.hibernate.dialects;

import java.sql.Types;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

public class SqliteDialect extends Dialect {
    public SqliteDialect() {
        super();

        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.VARCHAR, "text");
        registerColumnType(Types.LONGVARCHAR, "text");
        registerColumnType(Types.NVARCHAR, "text");
        registerColumnType(Types.LONGNVARCHAR, "text");
        registerColumnType(Types.DATE, "real");
    }    

    /**
     * {@inheritDoc}
     */
    @Override
	public IdentityColumnSupport getIdentityColumnSupport(){
        return new SqliteIdentityColumnSupport();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAlterTable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean dropConstraints() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDropForeignKeyString() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAddForeignKeyConstraintString(
        String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        return "";
    }
}
