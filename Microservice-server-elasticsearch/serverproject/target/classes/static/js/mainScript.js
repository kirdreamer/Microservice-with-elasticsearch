var userApi = Vue.resource('/user{/id}');

Vue.component('showDataElement', {
	props:['dataElement'],
	template: '<div> <p>' +
		'<div>Id: {{dataElement.id}} </div>' +
		'<div>First name: {{dataElement.firstName}}</div>' +
		'<div>Last name: {{dataElement.lastName}}</div>' +
		'<div>E-mail: {{dataElement.email}}</div>' +
		'<buttonsElements :dataElement="dataElement"/>' +
	'</p> </div>'
});


Vue.component('editLines', {
	props:['dataElement'],
    data: function() {
        return {
            newData: {
                    firstName: this.dataElement.firstName,
                    lastName: this.dataElement.lastName,
                    email: this.dataElement.email
                },
            seenVariable: false
        }
    },
	template: '<div> <p>' +
		'<input type="text" placeholder="New First Name" v-model="newData.firstName">First Name</input>' +
		'<br/>' +
		'<input type="text" placeholder="New Last Name" v-model="newData.lastName">Last Name</input>' +
		'<br/>' +
		'<input type="text" placeholder="New E-mail" v-model="newData.email">E-mail</input>' +
		'<br/>' +
		'<button v-on:click="updateMethod">Update</button>' +
		'<div v-if="seenVariable"> User was updated. Update the page to see the result</div>' +
	'</p> </div>',
	methods: {
	    updateMethod: function () {
                axios.patch('/user/' + this.dataElement.id, this.newData);
                this.seenVariable = !this.seenVariable;
	        }
    }
});

Vue.component('buttonsElements', {
	props:['dataElement'],
	data: function () {
		return {
			seenVariable: false
		}
	},
	template: '<div> <p>' +
		'<editLines v-if="seenVariable" :dataElement="dataElement"/>' +
		'<button v-on:click="editMethod">Edit</button>' + 
		 '<button v-on:click="deleteMethod">Delete</button>' +
	'</p> </div>',
	methods: {
		editMethod: function () {
			this.seenVariable = !this.seenVariable
		},
		deleteMethod: function() {
		    userApi.remove({id: this.dataElement.id})
		}
	}
});

Vue.component('showDataList', {
	props:['dataViewer'],
	template: '<div>' +
		'<showDataElement v-for="dataElement in dataViewer" :key="dataElement.id" v-bind:dataElement="dataElement"/>' +
	'</div>' ,
	created: function() {
	    userApi.get().then(result => result.json().then(data => data.forEach(user => this.dataViewer.push(user))))
	    }
});

Vue.component('inputNewData', {
    props:['dataViewer'],
	data: function() { 
		return {
		newData: {
                newFirstName: '',
                newLastName: '',
                newEmail: ''
		    }
		}
	},
	template: '<div> <p>' +
		'<input type="text" placeholder="First name of new user" v-model="newData.newFirstName" />' +
		'<input type="text" placeholder="Last name of new user" v-model="newData.newLastName" />' +
		'<input type="text" placeholder="Email of new user" v-model="newData.newEmail" />' +
		'<buttonCreate :newData="newData" :dataViewer="dataViewer"/>' +
	'</p> </div>'
});

Vue.component('buttonCreate', {
	props: ['newData', 'dataViewer'],
	template: '<input type="button" value="Add new user" v-on:click="saveMethod" />',
	methods: {
	    saveMethod: function() {
	        var user = {
	            firstName: this.newData.newFirstName,
	            lastName: this.newData.newLastName,
	            email: this.newData.newEmail,
	        }
	        userApi.save({}, user).then(result => result.json().then(data => {
                this.dataViewer.push(data);
	        }))
	    }
	}
});

var app = new Vue({
  el: '#simpleUi',
  template: '<div>Users on the service:' +
		'<inputNewData :dataViewer="dataViewer"/>' +
		'<showDataList v-bind:dataViewer="dataViewer"/>' + 
		'</div>',
  data: {
    dataViewer: []
  }
})
