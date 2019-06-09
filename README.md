# SimpleLineChart
[![](https://jitpack.io/v/tianma8023/SimpleLineChart.svg)](https://jitpack.io/#tianma8023/SimpleLineChart) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19) ![GitHub](https://img.shields.io/github/license/tianma8023/SimpleLineChart.svg?color=u)

Simple line chart inspired by [hellocharts](https://github.com/lecho/hellocharts-android)

## Features
- LineChart(cubic lines, normal lines)

## Screenshots
<img src="/ss/ss_linechart.jpeg" height="300"/>
<img src="/ss/ss_linechart_cubic.jpeg" height="300"/>


## Import
#### Android Studio / Gradle
1. To use this library you need add the jitpack.io repository to your **root** `build.gradle`:
    ```Groovy
    allprojects {
        repositories {
            jcenter()
            maven { url 'https://jitpack.io' } // add this line
        }
    }
    ```
    <font color='#FF4081'>**Note:** don't add the jitpack.io repository under `buildscript` closure.</font> 
2. Then add the SimpleLineChart dependency in your **module** `build.gradle`: 
    [![](https://jitpack.io/v/tianma8023/SimpleLineChart.svg)](https://jitpack.io/#tianma8023/SimpleLineChart)
    ```Groovy
    dependencies {
        // ...
        implementation 'com.github.tianma8023:SimpleLineChart:{latest_version}' 
    }
    ```

## Usage
A simple line chart could be placed in layout.xml:
```xml
    <com.tianma.chart.SimpleLineChart
        android:id="@+id/simple_line_chart"
        android:layout_width="match_parent"
        android:layout_height="200dp">
```

Simple line chart has its own method to set/add chart data, you can use it like this:
```java
    float[][] pointsData = new float[][]{
                new float[]{28, 27, 29.5f, 29, 29.2f},
                new float[]{23.5f, 23.7f, 23.3f, 24.4f, 23.5f}
    };

    List<Line> lines = new ArrayList<>();
    for (int i = 0; i < pointsData.length; i++) {
        List<Point> pointValues = new ArrayList<>();
        for (int j = 0; j < pointsData[i].length; j++) {
            pointValues.add(new Point(j, pointsData[i][j]));
        }
        Line line = new Line(pointValues);
        line.setLabelFormatter(new LineChartLabelFormatter() {
            @Override
            public String labelFormat(Point point) {
                return String.format(Locale.getDefault(), "%.1fº", point.getY());
            }
        });
        line.setLineColor(Color.BLACK); // set line color
        line.setLabelColor(ContextCompat.getColor(this, R.color.colorAccent)); // set label color
        line.setLabelTextSize(ResolutionUtils.sp2px(this, 12)); // set label text size
        // whether label is above point or not
        if (i == 0) {
            line.setLabelAbovePoint(true);
        } else {
            line.setLabelAbovePoint(false);
        }
        line.setCubic(isCubic); // whether line is cubic or not
        lines.add(line); 
    }

    LineChartData lineChartData = new LineChartData(lines);
    mSimpleLineChart.setLineChartData(lineChartData); // set chart data
```
## Demo / Sample

There is a sample project in this repo or you can download the demo apk directly from here [demo.apk](/demo/demo.apk)

## License
```txt
SimpleLineChart	
Copyright 2017 Tianma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```