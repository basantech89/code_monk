const timeElement = document.getElementById('title')
const timerElement = document.getElementById('timer')

function updateTimeElements() {
  const currentTime = new Date().toLocaleTimeString()
  timeElement.textContent = `The time is ${currentTime}`

  chrome.storage.local.get(['timer'], data => {
    const timer = data.timer ?? 0
    timerElement.textContent = `The timer is at ${timer}`
  })
}

updateTimeElements()
setInterval(updateTimeElements, 1000)

const nameElement = document.getElementById('name')
chrome.storage.sync.get(['name', 'test'], data => {
  nameElement.textContent = `Your name is ${data.name ?? '???'}`
})

const startBtn = document.getElementById('start')
const stopBtn = document.getElementById('stop')
const resetBtn = document.getElementById('reset')

startBtn.addEventListener('click', () => {
  chrome.storage.local.set({ isRunning: true })
})

stopBtn.addEventListener('click', () => {
  chrome.storage.local.set({ isRunning: false })
})

resetBtn.addEventListener('click', () => {
  chrome.storage.local.set({ isRunning: false, timer: 0 })
})