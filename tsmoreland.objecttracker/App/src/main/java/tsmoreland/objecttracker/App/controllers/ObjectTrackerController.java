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

package tsmoreland.objecttracker.app.controllers;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tsmoreland.objecttracker.app.models.Address;
import tsmoreland.objecttracker.app.models.LogModel;
import tsmoreland.objecttracker.app.models.ObjectAddModel;
import tsmoreland.objecttracker.app.models.ObjectModel;
import tsmoreland.objecttracker.app.models.ObjectSummaryModel;
import tsmoreland.objecttracker.data.services.ObjectDataService;

@RestController
public class ObjectTrackerController {

    @Autowired
    private ObjectDataService objectDataService;

    @PostMapping(
        value = "objects", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<?> putObject(@Validated @ModelAttribute @RequestBody ObjectAddModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(); // add new exception so we can include these in error response
        }

        var addedModel = new ObjectModel(1, "Fred", new Address(1, "Some St.", "Anyville"), Calendar.getInstance(), Collections.<LogModel>emptyList());

        return new ResponseEntity<>(addedModel, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "objects", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<List<ObjectSummaryModel>> getObjects() {

        return ResponseEntity.ok(List.<ObjectSummaryModel>of(
            new ObjectSummaryModel(1, "Fred"),
            new ObjectSummaryModel(2, "John")
        ));
    }

    @GetMapping(
        value = "objects/{id}", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<ObjectSummaryModel> getObjectById(@PathVariable int id) {
        return switch (id) {
            case 1 -> ResponseEntity.ok(new ObjectSummaryModel(1, "Fred"));
            case 2 -> ResponseEntity.ok(new ObjectSummaryModel(2, "John"));
            default -> ResponseEntity.notFound().build();
        };

    }

    @PostMapping(
        value = "objects/{id}/logs", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<?> putLog(@PathVariable int id, @Validated @ModelAttribute @RequestBody LogModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(); // add new exception so we can include these in error response
        }

        return switch(id) {
            case 1 -> new ResponseEntity<>(model, HttpStatus.CREATED);
            case 2 -> new ResponseEntity<>(model, HttpStatus.CREATED);
            default -> ResponseEntity.notFound().build();
        };
    }

    @GetMapping(
        value = "objects/{id}/logs", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<List<LogModel>> getLogs(@PathVariable int id) {

        return switch(id) {
            case 1 -> ResponseEntity.ok(List.<LogModel>of(
                new LogModel(0, "first message for Fred"),
                new LogModel(1, "second message for Fred")));
            case 2 -> ResponseEntity.ok(List.<LogModel>of(
                new LogModel(0, "first message for John"),
                new LogModel(1, "second message for John")));
            default -> ResponseEntity.notFound().build();
        };
    }

    @DeleteMapping(
        value = "objects/{id}", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public ResponseEntity<?> deleteObject(@PathVariable int id) {
        return switch(id) {
            case 1 -> ResponseEntity.noContent().build();
            case 2 -> ResponseEntity.noContent().build();
            default -> ResponseEntity.notFound().build();
        };
    }
}
