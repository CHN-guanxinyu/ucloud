# Ucloud
Simple network framework based on Akka.
## Usage
##### 1. add dependency
```
libraryDependencies += "io.github.tobetwo" % "ucloud" % "1.0.1-SNAPSHOT"
```
##### 2. implements `JobDriver`
```
package package.to
class Test extends JobDriver{
  override def runJob(args: String*): String = {
    println(1)
    "hi"
  }
}
```
##### 3. configuration
client.properties
```
ucloud.local.host=localhost
ucloud.local.port=10002
ucloud.local.name=Client-Foo
ucloud.local.job.config=path/to/jobs.xml
local.dir=/tmp

ucloud.server.host=localhost
ucloud.server.port=10001
```
server.properties
```
ucloud.local.host=localhost
ucloud.local.port=10001

rest.host=localhost
rest.port=20001

local.dir=/tmp/file

```
 jobs.xml
 ```
 <?xml version="1.0" encoding="UTF-8" ?>
 <job-list>
     <job>
         <name>foo</name>
         <desc>bar</desc>
         <type>test</type>
         <driver>package.to.Test</driver>
     </job>
 </job-list>
 ```
##### 4. run
```
//start server
java io.github.tobetwo.ucloud.Main \
--config-file /path/to/server.properties \
--mode server \
--action start

//start client
java io.github.tobetwo.ucloud.Main \
--config-file /path/to/client.properties \
--mode client \
--action start

```