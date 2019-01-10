import React, { Component } from 'react';
import { Text, View, ScrollView, StyleSheet, Picker, Switch, Button, Modal, Alert } from "react-native";
import { Card } from 'react-native-elements';
import DatePicker from 'react-native-datepicker';
import * as Animatable from 'react-native-animatable';
import { Permissions, Notifications, Calendar } from "expo";

class Reservation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            guests: 1,
            smoking: false,
            date: '',
            showModal: false
        }
    }

    static navigationOptions = {
        title: 'Reserve Table'
    };

    toggleModal() {
        this.setState({ showModal: !this.state.showModal })
    }

    handleReservation() {
        console.log(JSON.stringify(this.state));
        /*this.setState({
            guests: 1,
            smoking: false,
            date: ''
        });*/

        // we will toggle the modal instead
        // this.toggleModal();

        // using Alert instead of Modal
        Alert.alert(
            'Your Reservation OK?',
            'Number of Guests: ' + this.state.guests + '\nSmoking? ' +
            this.state.smoking + '\nDate and Time: ' + this.state.date,
            [
                {
                    text: 'Cancel',
                    onPress: () => this.resetForm(),
                    style: 'cancel'
                },
                {
                    text: 'OK',
                    onPress: () => {
                        this.presentLocalNotification(this.state.date);
                        this.resetForm();
                    }
                }
            ],
            { cancelable: false }
        );
        this.addReservationToCalendar(this.state.date);
    }

    resetForm() {
        this.setState({
            guests: 1,
            smoking: false,
            date: '',
            showModal: false
        });
    }

    // obtaining the permission for notification
    // async: because this is done asynchronously
    async obtainNotificationPermission() {
        // await: wait for the permission
        let permission = await Permissions.getAsync(Permissions.USER_FACING_NOTIFICATIONS);
        if (permission.status !== 'granted') {
            permission = await Permissions.askAsync(Permissions.USER_FACING_NOTIFICATIONS);
            if (permission.status !== 'granted') {
                Alert.alert('Permission not granted to show notifications');
            }
        }
        return permission;
    }

    async presentLocalNotification(date) {
        await this.obtainNotificationPermission();
        Notifications.presentLocalNotificationAsync({
            title: 'Your Reservation',
            body: 'Reservation for ' + date + ' requested',
            ios: {
                sound: true
            },
            android: {
                sound: true,
                vibrate: true,
                color: '#512DA8'
            }
        });
    }

    async obtainCalendarPermission() {
        const calendarPermission = await Permissions.askAsync(Permissions.CALENDAR);
        const reminderPermission = await Permissions.getAsync(Permissions.REMINDERS); // for IOS
        /*if (calendarPermission.status !== 'granted' && reminderPermission.status !== 'granted') {
            Alert.alert('Permissions are not granted ' + calendarPermission.status + ', ' + reminderPermission.status);
        }*/
        return [ calendarPermission, reminderPermission ];
    };

    async addReservationToCalendar(date) {
        const permissions = await this.obtainCalendarPermission();
        if (permissions[0].status === 'granted' && permissions[1].status === 'granted') {
            let currTime = Date.parse(date);
            let details = {
                title: 'Con Fusion Table Reservation',
                startDate: new Date(currTime),
                endDate: new Date(currTime + 2 * 60 * 60 * 1000),
                timeZone: 'Asia/Kolkata',
                location: '121, Clear Water Bay Road, Clear Water Bay, Kowloon, Hong Kong'
            };
            Calendar.createEventAsync(Calendar.DEFAULT, details);
        }
        else Alert.alert('Permissions are not granted ' + permissions[0].status + ', ' + permissions[1].status);

    }

    render() {
        return (
            <ScrollView>
                <Animatable.View animation="zoomIn" duration={1000} >
                    <View style={styles.formRow}>
                        <Text style={styles.formLabel}> Number of Guests </Text>
                        <Picker
                            style={styles.formItem}
                            selectedValue={this.state.guests}
                            onValueChange={
                                (itemValue, itemIndex) => this.setState({ guests: itemValue })
                            }
                        >
                            <Picker.Item label='1' value='1' />
                            <Picker.Item label='2' value='2' />
                            <Picker.Item label='3' value='3' />
                            <Picker.Item label='4' value='4' />
                            <Picker.Item label='5' value='5' />
                            <Picker.Item label='6' value='6' />
                        </Picker>
                    </View>

                    <View style={styles.formRow}>
                        <Text style={styles.formLabel}> Smoking/Non-Smoking? </Text>
                        <Switch
                            style={styles.formItem}
                            value={this.state.smoking}
                            onTintColor='#512DA8'
                            onValueChange={
                                (value) => this.setState({ smoking: value })
                            }
                        />
                    </View>

                    <View style={styles.formRow}>
                        <Text style={styles.formLabel}> Date and Time </Text>
                        <DatePicker
                            style={{ flex: 2, marginRight: 20 }}
                            date={this.state.date}
                            format=''
                            mode='datetime'
                            placeholder='select date and time' minDate='2017-01-01'
                            confirmBtnText='Confirm'
                            cancelBtnText='Cancel'
                            customStyles={{
                                dateIcon: {
                                    position: 'absolute',
                                    left: 0,
                                    top: 4,
                                    marginLeft: 0
                                },
                                dateInput: {
                                    marginLeft: 36
                                }
                            }}
                            onDateChange={
                                (date) => {this.setState({ date: date })}
                            }
                        />
                    </View>

                    <View style={styles.formRow}>
                        <Button
                            title={'Reserve'}
                            color='#512DA8'
                            onPress={ () => this.handleReservation() }
                            accessibilityLabel='Learn more about this purple button'
                        />
                    </View>

                    <Modal
                        animationType={'slide'}
                        transparent={false}
                        visible={this.state.showModal}
                        onDismiss={ () => { this.toggleModal(); this.resetForm() } }
                        onRequestClose={ () => { this.toggleModal(); this.resetForm() } }
                    >
                        <View style={styles.modal}>
                            <Text style={styles.modalTitle}> Your Reservation </Text>
                            <Text style={styles.modalText}> Number of Guests: {this.state.guests} </Text>
                            <Text style={styles.modalText}> Smoking?: {this.state.smoking ? 'Yes' : 'No'} </Text>
                            <Text style={styles.modalText}> Date and Time {this.state.date} </Text>
                            <Button
                                title='Close'
                                onPress={ () => { this.toggleModal(); this.resetForm() } }
                                color='#512DA8'
                            />
                        </View>
                    </Modal>
                </Animatable.View>
            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
    formRow: {
        alignItems: 'center',
        justifyContent: 'center',
        flex: 1,
        flexDirection: 'row',
        margin: 20
    },
    formLabel: {
        fontSize: 18,
        flex: 2,
    },
    formItem: {
        flex: 1
    },
    modal: {
        justifyContent: 'center',
        margin: 20
    },
    modalTitle: {
        fontSize: 24,
        fontWeight: 'bold',
        backgroundColor: '#512DA8',
        textAlign: 'center',
        color: 'white',
        marginBottom: 20
    },
    modalText: {
        fontSize: 18,
        margin: 10
    }
});

export default Reservation;
