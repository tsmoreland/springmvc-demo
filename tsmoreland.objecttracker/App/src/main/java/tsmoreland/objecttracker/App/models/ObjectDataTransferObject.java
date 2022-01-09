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
package tsmoreland.objecttracker.app.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ObjectModel {
    
    private int id;
    private String name;
    private Address address;
    private Calendar lastModifiedTime;
    private List<LogModel> logs;


    public ObjectModel(int id, String name, Address address, Calendar lastModifiedTime, List<LogModel> logs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lastModifiedTime = lastModifiedTime;
        this.logs = new ArrayList<LogModel>(logs);
    }
    public ObjectModel() {
        this.logs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Calendar getLastModifiedTime() {
        return lastModifiedTime;
    }
    public void setLastModifiedTime(Calendar lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
    public List<LogModel> getLogs() {
        return logs;
    }
    public void setLogs(List<LogModel> logs) {
        this.logs = logs;
    }
}
