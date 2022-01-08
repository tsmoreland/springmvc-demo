//
// Copyright © 2021 Terry Moreland
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
// to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
package tsmoreland.objecttracker.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tsmoreland.objecttracker.core.models.LogEntry;
import tsmoreland.objecttracker.core.models.Severity;
import tsmoreland.objecttracker.shared.ComparisonUtilities;

@Entity
@Table(name = "LogEntity")
public class LogEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id = Long.valueOf(0);
    
    @Column(name = "Message", nullable = false, unique = false)
    private String message = "";

    @Column(name = "Severity", nullable = false, unique = false)
    private Integer severity = Integer.valueOf(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ObjectEntityId")
    private ObjectEntity objectEntity;

    public LogEntryEntity() {

    }
    public LogEntryEntity(String message, Integer severity,  ObjectEntity objectEntity) {
        this.message = message;
        this.severity = severity;
        this.objectEntity = objectEntity;
    }
    public LogEntryEntity(Long id, String message, Integer severity,  ObjectEntity objectEntity) {
        this.id = id;
        this.message = message;
        this.severity = severity;
        this.objectEntity = objectEntity;
    }
    public LogEntryEntity(LogEntry model, ObjectEntity objectEntity) {
        this.message = model.message();
        this.severity = model.severity().asInteger();
        this.objectEntity = objectEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public ObjectEntity getObjectEntity() {
        return objectEntity;
    }

    public void setObjectEntity(ObjectEntity objectEntity) {
        this.objectEntity = objectEntity;
    }

    public LogEntry ToLogEntry() {
        return new LogEntry(message, Severity.FromInteger(severity));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ComparisonUtilities.updateHashCode(result, prime, message);
        result = ComparisonUtilities.updateHashCode(result, prime, objectEntity);
        result = ComparisonUtilities.updateHashCode(result, prime, severity);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof LogEntryEntity other)) {
            return false;
        }

        return
            ComparisonUtilities.areEqual(message, other.message) &&
            ComparisonUtilities.areEqual(message, other.objectEntity) &&
            ComparisonUtilities.areEqual(message, other.severity); 
    }
}
