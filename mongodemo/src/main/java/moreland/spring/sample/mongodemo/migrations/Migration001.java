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
package moreland.spring.sample.mongodemo.migrations;

import java.util.Arrays;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import org.springframework.data.mongodb.core.MongoTemplate;

import moreland.spring.sample.mongodemo.domain.Country;

@ChangeLog(order = "001")
public class Migration001 {
    
    @ChangeSet(order = "001", id = "seedCountries", author = "Terry Moreland") 
    public void seed(MongoTemplate mongoTemplate) {
        var canada = new Country("1576624e-8340-4f7e-8b37-ccc6949dfcda", "Canada");
        var uk = new Country("c7f244cd-11d9-4535-8be7-fa85723ee4a4", "United Kingdom");
        var ireland = new Country("0c85982b-f28c-4d19-a338-34964d2fc048", "Ireland");

        mongoTemplate.insertAll(Arrays.asList(canada, uk, ireland));
    }
}
