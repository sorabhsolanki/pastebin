# pastebin

1. User can upload any text/image/link and can tag those.
2. Upon successful uploading, a unique tiny url will get generated.
3. User can share that url and upon clicking that url the uploaded information will get displayed.
4. User can also search the information from tag.

User can be of two types {guest, loggedIn user}
Currently pastebin will only support guest user.

Entities
--------
url --> contains the url. One url can be mapped to multi documents.
document --> contains the document, it can be text/link or image.
document_tag --> contains the info which tag is mapped to which document.
tag --> contains the tag information.

Multiple tag can be mapped to multiple documents.




