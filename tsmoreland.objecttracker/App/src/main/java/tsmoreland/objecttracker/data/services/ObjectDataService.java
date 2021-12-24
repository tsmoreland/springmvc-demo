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


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tsmoreland.objecttracker.core.models.LogEntry;
import tsmoreland.objecttracker.core.models.ObjectModel;
import tsmoreland.objecttracker.core.projections.ObjectSummaryModel;

public interface ObjectDataService {
    
    ObjectModel addObjectModel(ObjectModel model);

    Optional<ObjectSummaryModel> findById(Long id);
    Page<ObjectSummaryModel> findAll(Pageable page);
    Page<LogEntry> findAllLogs(Long id, Pageable page);

    void updateObjectModel(ObjectModel model);
    void deleteObjectModel(Long id);
    void deleteObjectModel(ObjectModel model);

}
