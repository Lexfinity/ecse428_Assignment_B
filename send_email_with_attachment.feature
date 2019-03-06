Scenario: Sending an email with an image file attachment
  Given I am a user
  When I import an image file to my email
  Then that file should appear as an attachment(s)
  And I can send the email with the attachment(s)

Scenario: Sending an email with a large image file attachment
  Given I am a user
  When I upload an image larger than 25mb
  Then it will automatically be uploaded to my google drive
  And I can send an email with the google drive link to the attachment(s)

Scenario:  Sending an email with an image file attachment while it is still being uploaded
  Given I am a user
  When I upload an image file to my email
  And I press send while the files are still uploading
  Then my email will not be sent
  And I will get a message saying to wait until my files are uploaded
