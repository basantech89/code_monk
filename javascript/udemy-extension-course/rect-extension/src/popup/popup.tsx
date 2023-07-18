import React from 'react'
import ReactDOM from 'react-dom/client'
import './popup.css'

const Test = () => <img src='icon.png' />

const div = document.createElement('div')
document.body.appendChild(div)

const root = ReactDOM.createRoot(div)

root.render(
  <React.StrictMode>
    <Test />
  </React.StrictMode>
)
