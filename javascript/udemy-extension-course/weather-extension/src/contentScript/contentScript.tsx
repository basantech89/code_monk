import React from 'react'
import ReactDOM from 'react-dom'
import WeatherCard from '../components/WatherCard'
import { Card } from '@material-ui/core'

import './contentScript.css'
import { LocalStorageOptions, getStoredOptions } from '../utils/storage'
import { Messages } from '../utils/messages'

const App = () => {
	const [options, setOptions] = React.useState<LocalStorageOptions | null>(null)
	const [isActive, setIsActive] = React.useState(false)

	React.useEffect(() => {
		getStoredOptions().then(options => {
			setOptions(options)
			setIsActive(options.hasAutoOverlay)
		})
	}, [])

	React.useEffect(() => {
		chrome.runtime.onMessage.addListener(msg => {
			if (msg === Messages.TOGGLE_OVERLAY) {
				setIsActive(!isActive)
			}
		})
	}, [isActive])

	if (!options) {
		return null
	}

	return (
		<>
			{isActive && (
				<Card className='overlayCard'>
					<WeatherCard
						city={options.homeCity}
						tempScale={options.tempScale}
						onDelete={() => setIsActive(false)}
					/>
				</Card>
			)}
		</>
	)
}

const root = document.createElement('div')
document.body.appendChild(root)
ReactDOM.render(<App />, root)
