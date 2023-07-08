chrome.runtime.onInstalled.addListener(details => {
  // set default values
  chrome.storage.local.set({ shows: [] })
  
  chrome.contextMenus.create({
    title: 'Search TV Show',
    id: 'contextMenu1',
    contexts: ['page', 'selection']
  })

  chrome.contextMenus.create({
    title: 'Read This Text',
    id: 'contextMenu2',
    contexts: ['page', 'selection']
  })

  chrome.contextMenus.onClicked.addListener(event => {
    if (event.menuItemId === 'contextMenu1') {
      // chrome.tabs.create({ url: `https://www.imdb.com/search/keyword/?keywords=${event.selectionText}` })
      fetch(`https://api.tvmaze.com/search/shows?q=${event.selectionText}`)
        .then(res => res.json())
        .then(data => {
          console.log(data)
          chrome.storage.local.set({ shows: data })
        })
    } else if (event.menuItemId === 'contextMenu2') {
      chrome.tts.speak(event.selectionText, {
        lang: 'zh-CN',
        rate: 1
      })
    }
  })
})
