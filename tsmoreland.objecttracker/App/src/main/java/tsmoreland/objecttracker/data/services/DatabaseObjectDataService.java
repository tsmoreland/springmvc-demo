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
package tsmoreland.objecttracker.data.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tsmoreland.objecttracker.core.models.LogEntry;
import tsmoreland.objecttracker.core.models.ObjectModel;
import tsmoreland.objecttracker.core.models.Severity;
import tsmoreland.objecttracker.core.projections.ObjectSummaryModel;
import tsmoreland.objecttracker.data.entities.ObjectEntity;
import tsmoreland.objecttracker.data.repositories.ObjectRepository;

@Service
public class DatabaseObjectDataService implements ObjectDataService {

    @Autowired
    private ObjectRepository repository;


    @Override
    public ObjectModel addObjectModel(ObjectModel model) {
        return repository
            .save(ObjectEntity.FromObjectModel(model))
            .ToObjectModel(false);
    }

    @Override
    public Optional<ObjectModel> findById(Long id, boolean includeLogs) {
        return repository.findById(id).map(o -> o.ToObjectModel(includeLogs));
    }

    @Override
    public List<ObjectSummaryModel> findAll(Pageable page) {
        /*
        return repository
            .findAllViews(page)
            .stream()
            .map(o -> new ObjectSummaryModel(o.getId(), o.getName()))
            .collect(Collectors.toList());
            */
        return null;            
    }

    @Override
    public List<LogEntry> findAllLogs(Long id, Pageable page) {
        /*
        return repository
            .findLogsByObjectEntityId(id, page)
            .stream()
            .map(l -> new LogEntry(l.getMessage(), Severity.FromInteger(l.getSeverity())))
            .collect(Collectors.toList());
        */
        return null;            
    }

    @Override
    public void updateObjectModel(ObjectModel model) {

        if (!model.getId().isPresent()) {
            throw new  IllegalArgumentException("model must have a valid id");
        }

        ObjectEntity existing = repository
            .findById(model.getId().get())
            .orElseThrow();
        
        existing.Update(model);
        repository.save(existing);
    }

    @Override
    public void deleteObjectModel(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteObjectModel(ObjectModel model) {
        if (!model.getId().isPresent()) {
            throw new  IllegalArgumentException("model must have a valid id");
        }
        repository.deleteById(model.getId().get());
    }
    
}
