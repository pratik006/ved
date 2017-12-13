import { Chapter } from './chapter';
export class Book {
  _id: number;
	name: string;
	authorName: string;
	previewUrl: string;
	chapters: Chapter[];

	get id(): number { return this.id; }
	set id(theId: number) {	this._id = theId;	}
}
