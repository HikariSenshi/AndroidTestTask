# AndroidTestTask
Application A 

It consists of two tabs (test and history).
On the "test" tab there is an input field and the "ok" button. In the field you need to enter a link to the image. 
When you click on "ok", application B should open.
The tab "history" contains a list of open links. Important: Application A should not make changes to this list (insert / delete / change).
In action bar there should be a button for sorting links - by the date of addition (from new to old) and by status. 
The link reference should be green for status 1, red for status 2, gray for status 3.
Clicking on the link should also open application B.
The history list should automatically change when changes are made to the database.
 
 
Application B

If application B is opened by clicking the "ok" button with the "test" in Appendix A, then application B should store this link in database A with reference fields, status (1 - downloaded, 2 - error, 3 - unknown) and time ( opening time B) and output this picture.
If the appendix B is opened by links from the tab "story", then you need to show this picture, and when you open the green link - this link should be deleted after 15 seconds from the base A, and show the message that the link has been deleted, even if the appendix B is closed ; and also save this picture in transit / sdcard / BIGDIG / test / B
When opening a red or gray link - their status should be updated if it has changed.
If application B is opened from the lunch list, then a message should be displayed on the screen that application B is not a stand-alone application and will be closed after n seconds (reverse timer). You need to count back 10 seconds and close the application.
