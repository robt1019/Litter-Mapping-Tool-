/**
 * Custom functions for Litter Mapping Tool
 */

function printTimeStamp(timeStamp) {
	var monthNames = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	var date = new Date(timeStamp);
	var printDate = monthNames[date.getMonth()] + " " + date.getDate() + ", " + date.getFullYear();
	var printTime = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes());
	return [ printDate, printTime ];
}

function printCompactTimeStamp(timeStamp) {
	var date = new Date(timeStamp);
	var month = date.getMonth() + 1;
	var printDate = (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "/" + (month < 10 ? "0" + month : month) + "/" + date.getFullYear();
	var printTime = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes());
	return [printDate, printTime];
}

// JavaScript HashTable implementation
// From: http://www.mojavelinux.com/articles/javascript_hashes.html

function HashTable(obj) {
	this.length = 0;
	this.items = {};
	for ( var p in obj) {
		if (obj.hasOwnProperty(p)) {
			this.items[p] = obj[p];
			this.length++;
		}
	}

	this.put = function(key, value) {
		var previous = undefined;
		if (this.hasItem(key)) {
			previous = this.items[key];
		} else {
			this.length++;
		}
		this.items[key] = value;
		return previous;
	}

	this.getItem = function(key) {
		return this.hasItem(key) ? this.items[key] : undefined;
	}

	this.hasItem = function(key) {
		return this.items.hasOwnProperty(key);
	}

	this.removeItem = function(key) {
		if (this.hasItem(key)) {
			previous = this.items[key];
			this.length--;
			delete this.items[key];
			return previous;
		} else {
			return undefined;
		}
	}

	this.keys = function() {
		var keys = [];
		for ( var k in this.items) {
			if (this.hasItem(k)) {
				keys.push(k);
			}
		}
		return keys;
	}

	this.values = function() {
		var values = [];
		for ( var k in this.items) {
			if (this.hasItem(k)) {
				values.push(this.items[k]);
			}
		}
		return values;
	}

	this.each = function(fn) {
		for (var k in this.items) {
			if (this.hasItem(k)) {
				fn(k, this.items[k]);
			}
		}
	}

	this.clear = function() {
		this.items = {}
		this.length = 0;
	}
}

function getMostPrevalentLitter(h) {
	
	var items = [];
	var mpItems = [];
	
	for (var i = 0, keys = h.keys(), len = keys.length; i < len; i++) {
		items.push([keys[i], h.getItem(keys[i])]);
	}
	
	items.sort(sortFunction);
	
	for (var i = 0; i < items.length; i++) {
		if (i < 3) {
			mpItems.push(items[i]);
		} else {
			break;
		}
	}
	
	return mpItems;
}

function sortFunction(a, b) {
    if (a[1] === b[1]) {
        return 0;
    } else {
        return (a[1] > b[1]) ? -1 : 1;
    }
}

// Date difference

var DateDiff = {

    inDays: function(d1, d2) {
        var t2 = d2.getTime();
        var t1 = d1.getTime();

        return parseInt((t2-t1)/(24*3600*1000));
    },

    inWeeks: function(d1, d2) {
        var t2 = d2.getTime();
        var t1 = d1.getTime();

        return parseInt((t2-t1)/(24*3600*1000*7));
    },

    inMonths: function(d1, d2) {
        var d1Y = d1.getFullYear();
        var d2Y = d2.getFullYear();
        var d1M = d1.getMonth();
        var d2M = d2.getMonth();

        return (d2M+12*d2Y)-(d1M+12*d1Y);
    },

    inYears: function(d1, d2) {
        return d2.getFullYear()-d1.getFullYear();
    }
}

// Date - add days

Date.prototype.addDays = function(days) {
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
}