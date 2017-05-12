#player

####项目的整体架构 (学习的知识点)

  1 整个项目使用了 MD这种扁平化的风格

  2 使用MVP架构模式，整个项目结构清晰

  3 使用RX这种响应式，完成异步操作，以及合理利用它的操作符，很强大的功能

  4 使用并封装了Glide图片缓存框架

  5 集成了ijkplayer完成视频播放

  6 使用百分比的适配方式，轻松适配所有分辨率的机型 （DensityUtils.measure(imageView, 1080, 540);）

  7 多线程的断点下载，封装第三方（filedownloader库）的下载工具类（DownloadUtil），使用方便