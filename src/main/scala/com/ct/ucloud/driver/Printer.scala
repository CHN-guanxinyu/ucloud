package com.ct.ucloud.driver

import com.ct.ucloud.{DeviceDriver, FileManager}

class Printer extends DeviceDriver{

  override def runJob(args: String*) = FileManager.download(args.head){ file =>
    println(file)
  }


}
