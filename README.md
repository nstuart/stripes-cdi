Overview
========

This is a simple extension the Stripes Framework (http://stripesframework.org/display/stripes/Home)
to allow for control over most, if not all, Stripes resources though CDI.

The purpose of this project is simple.  I was looking for a simple way to get an MVC style application
that integrates with CDI/EE6. The other option, besides Stripes, is to use something like Spring MVC to
and integrate that with CDI/EE6. While this is certainly possible, I have used Stripes in the passed and
have enjoyed 'ease' of use and conventions.

Also, using something like Spring MVC bring a lot more to the table then just an MVC framework. Not that
Spring is a bad solution, but looking for using *JUST* EE6 and CDI as the glue and Stripes as the 
presentation layer.

Currently we rely in the latest source from Stripes (1.6.0-SNAPSHOT) as it provides all the necessary 
integration points to hook our claws into and control just about every aspect of the application. I 
am hoping that very little modifications will be needed in order to get full integration, but I beleive
some small changes will be needed for eveything to work smoothly.

Currently Tested
================

* Simple Action Bean injection and control. Both RequestScoped, and Dependent scoped beans. (SessionScoped should be fine, just have not tested)
* URL mapping, lookup, and generation of CDI managed Action beans.
* RequestScoped variables and validation within ActionBeans (models).

Currently Broken
================

* BeforeAfterMethodInterceptor currently does not work with scoped beans as the Proxy is getting in the way.
* Validation (?)


