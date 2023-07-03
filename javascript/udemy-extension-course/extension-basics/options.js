const nameInput = document.getElementById('name-input')
const saveBtn = document.getElementById('save-btn')
const timeInput = document.getElementById('time-input')

saveBtn.addEventListener('click', () => {
  const name = nameInput.value
  const notificationTime = timeInput.value

  chrome.storage.sync.set({ name, notificationTime })
})

chrome.storage.sync.get(['name', 'notificationTime'], data => {
  nameInput.value = data.name ?? '???'
  timeInput.value = data.notificationTime ?? 1000
})
