chrome.webRequest.onBeforeRequest.addListener(details => {
  const filters = [
    'googleadservices',
    'googlesyndication',
    'g.doubleclick'
  ]

  const url = details.url
  for (const filter of filters) {
    if (url.indexOf(filter) !== -1) {
      console.log(url)
      return { cancel: true }
    }
  }

  return { cancel: false }
}, {
  urls: ['<all_urls>']
}, ['blocking'])