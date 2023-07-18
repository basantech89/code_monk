import React from 'react'
import ReactDOM from 'react-dom'

import 'fontsource-roboto'
import './options.css'
import {
	Box,
	Button,
	Card,
	CardContent,
	Grid,
	Switch,
	TextField,
	Typography
} from '@material-ui/core'
import {
	LocalStorageOptions,
	getStoredOptions,
	setStoredOptions
} from '../utils/storage'

type FormState = 'ready' | 'saving'

const App: React.FC<{}> = () => {
	const [options, setOptions] = React.useState<LocalStorageOptions | null>(null)
	const [formState, setFormState] = React.useState<FormState>('ready')

	React.useEffect(() => {
		getStoredOptions().then(setOptions)
	}, [])

	if (!options) {
		return null
	}

	const changeCity = (homeCity: string) => {
		setOptions({ ...options, homeCity })
	}

	const saveOptions = () => {
		setFormState('saving')
		setStoredOptions(options).then(() => {
			setTimeout(() => {
				setFormState('ready')
			}, 1000)
		})
	}

	const toggleAutoOverlay = (hasAutoOverlay: boolean) => {
		setOptions({ ...options, hasAutoOverlay })
	}

	return (
		<Box mx='10%' my='2%'>
			<Card>
				<CardContent>
					<Grid container direction='column' spacing={4}>
						<Grid item>
							<Typography variant='h4'>Weather Extension Options</Typography>
						</Grid>
						<Grid item>
							<Typography variant='body1'>Home city name</Typography>
							<TextField
								fullWidth
								placeholder='Enter a home city'
								value={options.homeCity}
								disabled={formState === 'saving'}
								onChange={event => changeCity(event.target.value)}
							/>
						</Grid>
						<Grid item>
							<Typography variant='body1'>
								Auto Toggle Overlay on webpage load
							</Typography>
							<Switch
								color='primary'
								checked={options.hasAutoOverlay}
								disabled={formState === 'saving'}
								onChange={(event, checked) => toggleAutoOverlay(checked)}
							/>
						</Grid>
						<Grid item>
							<Button
								variant='contained'
								color='primary'
								disabled={formState === 'saving'}
								onClick={saveOptions}
							>
								{formState === 'ready' ? 'Save' : 'Saving...'}
							</Button>
						</Grid>
					</Grid>
				</CardContent>
			</Card>
		</Box>
	)
}

const root = document.createElement('div')
document.body.appendChild(root)
ReactDOM.render(<App />, root)
