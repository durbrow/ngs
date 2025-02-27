ngs
===

## ANNOUNCEMENTS:

December 10, 2021

NCBI's SRA will change the source build system to use CMake in the next toolkit release (date TBD). This change is an important step to improve developersí productivity as it provides unified cross platform access to support multiple build systems. This change affects developers building NCBI SRA tools from source. Old makefiles and build systems will no longer be supported after we make this change.

This change will also include the structure of GitHub repositories, which will undergo consolidation to provide an easier environment for building tools and libraries (NGS libs and dependencies are consolidated). Consolidation of NGS libraries and dependencies provides better usage scope isolation and makes building more straightforward.

### **Affected repositories**

1) ncbi/ngs (https://github.com/ncbi/ngs)

   This repository will be frozen, and all the code moved to Github repository ncbi/sra-tools, under subdirectory ngs/. All future modifications       
   will take place in sra-tools

2) ncbi/ncbi-vdb (https://github.com/ncbi/ncbi-vdb)

   This projectís build system will be based on CMake. The libraries supporting access to VDB data via NGS API will be moved to Github repository 
   ncbi/sra-tools. 

The projects to move are: 

| Old location (base URL: https://github.com/ncbi/ncbi-vdb) | New location (base URL: https://github.com/ncbi/sra-tools) |
| --------------------------------------------------------- | ---------------------------------------------------------- |
| libs/ngs | ngs/ncbi/ngs |
| libs/ngs-c++ | ngs/ncbi/ngs-c++ |
| libs/ngs-jni | ngs/ncbi/ngs-jni |
| libs/ngs-py | ngs/ncbi/ngs-py |
| libs/vdb-sqlite | libs/vdb-sqlite |
| test/ngs-java | test/ngs-java |
| test/ngs-python | test/ngs-python |


3) ncbi/sra-tools (https://github.com/ncbi/sra-tools)

   This projectís build system will be based on CMake. The project will acquire some new components:

       3a) NGS SDK (now under ngs/, formerly in Github repository ncbi/ngs)
       
       3b) NGS-related VDB access libraries and their dependents, formerly in Github repository ncbi/ncbi-vdb, as listed in the table above.


# NGS Language Bindings

NGS is a new, domain-specific API for accessing reads, alignments and pileups produced from Next Generation Sequencing. The API itself is independent from any particular back-end implementation, and supports use of multiple back-ends simultaneously. It also provides a library for building new back-end "engines". The engine for accessing SRA data is contained within the sister repository [ncbi-vdb](https://github.com/ncbi/ncbi-vdb).

The API is currently expressed in C++, Java and Python languages. The design makes it possible to maintain a high degree of similarity between the code in one language and code in another - especially between C++ and Java.

# Distribution of NGS APIs and Examples

You can find information about building and running examples in README file located in [tar archives](https://github.com/ncbi/ngs/wiki/Downloads).

# Documentation

Further documentation is available on the [wiki](https://github.com/ncbi/ngs/wiki).
