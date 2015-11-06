cantaa-commons
==============
Release:

2.1 start 06.11.2015
- New methods in DateUtil

2.0 start 30.07.2015
- StringUtil: remove deprecated concatenate-Methods
- StringUtil.join: Iterable of Object changed to Iterable of any type

1.9 start 17.06.2015)
- New: extend loaded properties with additional properties from external file

1.8
- StringUtil.join now takes an iterable instead of a list
- cosmetic: log.debug instead of info

1.7
- new: Cache.list(Type)
- fix: AppType was still read from System-Settings in one place. Corrected to Environment
- new: Wicket: New FocusOnLoadBehavior

1.6
- Start Bundle-Externalization
- new EnumUtil + ValueHolder
- Wicket AbstractApplicationFactory no longer writes the appType to a system property but uses a static variable in class Environment

1.5
- Time, TimeConverter
- PlaceHolderConfigurer, Prop.loader
- Cache: Multikey-Aware
- BooleanModel
- JQuery/Ajax-Wicket
- Convenient Generic Comparator
- Dependencies set to optional

1.2-SNAPSHOT
  - new PercentageConverter (like MoneyConverter)
  - Types Money, Percentage, and Quantity registered in SafeModel

1.1
  First release to Maven Central

External dependancies:
- Global
  * slf-log4j12
  * junit4 (Test only)

- Spring-Package
  * spring-core, -beans, -web, -orm
  * javax.persistence

- Wicket-Package
  * wicket-core:
  * wicket-spring:
  * javassist (SafeModel)

All dependencies are marked as optional