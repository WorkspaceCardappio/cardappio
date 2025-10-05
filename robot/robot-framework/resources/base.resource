*** Settings ***
Library    Browser

Resource   env.resource

*** Keywords ***
Start Session
    New Browser    browser=${BROWSER}    headless=${false}
    New Page       ${BASE_URL}