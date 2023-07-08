const text = []

const aTags = document.getElementsByTagName('a')
for (const aTag of aTags) {
  text.push(aTag.textContent)
}

// by default this sends the message to all other background scripts and popups on your extension
chrome.runtime.sendMessage(null, text, resposne => {
  console.log(`I'm from the send response function! ${resposne}`);
})

chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  console.log('content script listener', message, sender);
})