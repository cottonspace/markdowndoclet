# MarkdownDoclet
Doclet which creates Javadoc as Markdown file.

## Homepage

[http://www.csync.net/blog/pc/markdowndoclet/](http://www.csync.net/blog/pc/markdowndoclet/)

## How to use as Ant task

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<project default="pandoc">
  <target name="javadoc">
    <javadoc access="private" additionalparam="-encoding utf-8" packagenames="doclet.markdown" sourcepath="src">
      <doclet name="doclet.markdown.MarkdownDoclet" path="markdowndoclet-1.0.jar">
        <param name="-file" value="document.md" />
        <param name="-title" value="SUBJECT" />
        <param name="-subtitle" value="SUBTITLE" />
        <param name="-version" value="VER 1.0" />
        <param name="-company" value="XXX PROJECT" />
      </doclet>
    </javadoc>
  </target>
</project>
```

## Copyright and License
All the source code avaiable in this repository is licensed under the **[GPL, Version 3.0](http://www.gnu.org/licenses)**
