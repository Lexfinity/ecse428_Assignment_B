Feature: Test sending email with attachment

  Scenario: Sending an email with an image file attachment
    Given I am a user
    And I have clicked "compose a new email"
    And I have filled in the information for a recepient email and subject
    When I import an image file to my email
    Then that file should appear as an attachment
    And I can send the email with the attachment

  Scenario: Sending an email with an image file attachment to multiple recipients
    Given I am a user
    And I have clicked "compose a new email"
    And I have filled in the information for two recepient emails and subject
    When I import an image file to my email
    Then that file should appear as an attachment
    And I can send the email with the attachment

  Scenario: Sending an email with a large image file attachment
    Given I am a user
    And I have clicked "compose a new email"
    And I have filled in the information for a recepient email and subject
    When I upload an image larger than 25mb
    Then it will automatically be uploaded to my google drive and attached to email
    And I can send an email with the google drive link to the attachment

  Scenario:  Sending an email without an image file attached to it
    Given I am a user
    And I have clicked "compose a new email"
    And I have filled in the information for a recepient email and subject
    When I upload an image file to my email
    And I cancel the upload
    Then my email will be sent without an image attachment