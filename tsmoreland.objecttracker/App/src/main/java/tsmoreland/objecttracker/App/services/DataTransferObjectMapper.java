package tsmoreland.objecttracker.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import tsmoreland.objecttracker.app.models.LogDataTransferObject;
import tsmoreland.objecttracker.app.models.ObjectAddDataTransferObject;
import tsmoreland.objecttracker.app.models.ObjectDataTransferObject;
import tsmoreland.objecttracker.core.models.LogEntry;
import tsmoreland.objecttracker.core.models.ObjectModel;

@Service
public class DataTransferObjectMapper {
    
    public ObjectModel objectModelFromAddedDataTransferObject(ObjectAddDataTransferObject dataTransferObject) {
        return new ObjectModel(dataTransferObject.getName(), dataTransferObject.getProgress());
    }

    public ObjectDataTransferObject objetModelToDataTransferObject(ObjectModel model) {

        List<LogDataTransferObject> logs = StreamSupport
            .stream(model.getLogEntries().spliterator(), false)
            .map(this::logEntryToDataTransferObject)
            .collect(Collectors.toList());

        Optional<List<LogDataTransferObject>> optionalLogs = logs.size() > 0
            ? Optional.of(logs)
            : Optional.empty();

        return new ObjectDataTransferObject(
            model.getId().orElseThrow(() -> new IllegalArgumentException("invalid model")).longValue(),
            model.getName(),
            model.getProgress(),
            optionalLogs);
    }

    public LogDataTransferObject logEntryToDataTransferObject(LogEntry model) {
        return new LogDataTransferObject(
            model.severity().asInt(),
            model.message());
    }
}
