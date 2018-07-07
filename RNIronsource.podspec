require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RNIronsource"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  RNIronsource
                   DESC
  s.homepage     = "https://github.com/rf1804/react-native-ironsource#readme"
  s.license      = "MIT"
  s.license    = { :type => "MIT", :file => "LICENSE" }
  s.author       = { "author" => "rfraja2943@gmail.com" }
  s.pl0atform     = :ios, "7.0"
  s.source       = { :git => "#{package_json["repository"]["url"]}.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'IronSourceSDK','6.7.9.3'
end
