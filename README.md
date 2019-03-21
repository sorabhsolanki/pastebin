# pastebin

1. User can paste any text/link or upload any image/file and can tag those things.
2. Upon successful uploading, a unique tiny url will get generated.
3. User can share that url and upon clicking that url the uploaded information will get displayed along with tags.
4. User/other users can also search the information from tag.

Currently all information are public{no support for user management as of now}.

Entities
--------
url --> contains the url. One url can be mapped to multiple documents. {since user can upload multi infomation on same page and then requequest for url.}
document --> contains the document, it can be text/link/file/image, In case of file and image document store the actual directory location.
document_tag --> contains the info which tag is mapped to which document.
tag --> contains the tag information.

Multiple tag can be mapped to multiple documents.




