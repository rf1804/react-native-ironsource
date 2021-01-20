require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RNIronsource"
  s.version      = package["version"]
  s.summary      = package["title"]
  s.description  = package["description"]
  s.homepage     = package["homepage"]
  # s.license      = "MIT"
  s.license    = { :type => package["license"], :file => package["licenseFilename"] }
  s.author       = package["author"]
  s.platform     = :ios, "11.0"
  s.source       = { :git => package["repository"]["url"], :tag => "#{s.version}" }
  s.compiler_flags = '–ObjC'

  s.source_files = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'IronSourceSDK','6.7.9.3'
end
