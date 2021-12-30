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

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tsmoreland.objecttracker.core.models.LogEntry;
import tsmoreland.objecttracker.core.models.ObjectModel;

@Entity
@Table(name = "Objects")
public class ObjectEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @Column(name = "Name", nullable = false, unique = false)
    private String name;

    @Column(name = "LastModified", nullable = false, unique = false)
    private String lastModified;

    @Column(name = "Progress", nullable = false, unique = false)
    private Integer progress = Integer.valueOf(0);

    @OneToMany(
        mappedBy = "objectEntity", 
        fetch = FetchType.LAZY,
        orphanRemoval = true)
    private List<LogEntryEntity> logEntries = new ArrayList<>();

    public ObjectEntity(Long id, String name, Integer progress, String lastModified) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.lastModified = lastModified;
    }

    public ObjectEntity() {
        id = Long.valueOf(0);
        name = "";
        progress = Integer.valueOf(0);
        // keep this value sipmle, attempts to calculate it using current time break the build
        lastModified = "0001-01-01 00:00:00";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void addLogEntry(LogEntry model) {
        logEntries.add(new LogEntryEntity(model.message(), model.severity().asInteger(), this));
    }

    public void Update(ObjectModel model) {
        name = model.getName();
        progress = Integer.valueOf(model.getProgress());

        for (LogEntry entry : model.getLogEntries()) {
            LogEntryEntity entity = new LogEntryEntity(entry, this);
            if (!this.logEntries.contains(entity)) {
                continue; // already present
            } else {
                logEntries.add(entity);
            }
        }
    }

    public ObjectModel ToObjectModel(boolean includeLogs) {
        if (!includeLogs) {
            return new ObjectModel(Optional.of(id), name, progress.intValue(), List.of());
        }

        var logEntyModels = logEntries.stream()
            .map(LogEntryEntity::ToLogEntry)
            .collect(Collectors.toList());
        return  new ObjectModel(Optional.of(id), name, progress.intValue(), logEntyModels);
    }

    public static ObjectEntity FromObjectModel(ObjectModel model) {

        Long id = model.getId().orElse(Long.valueOf(0));
        String name = model.getName();
        Integer progress = Integer.valueOf(model.getProgress());
        String lastModified = getNowAsFormattedString();

        var entity = new ObjectEntity(id, name, progress, lastModified);

        for (LogEntry entry : model.getLogEntries()) {
            LogEntryEntity logEntity = new LogEntryEntity(entry, entity);
            if (!entity.logEntries.contains(logEntity)) {
                continue; // already present
            } else {
                entity.logEntries.add(logEntity);
            }
        }
        return entity;
    }

    private static String getNowAsFormattedString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant now = Instant.now();
        return formatter.format(now);
    }
}
