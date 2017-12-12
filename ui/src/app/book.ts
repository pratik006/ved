import { Sutra } from './sutra';
export class Book {
  id: number;
	name: string;
	authorName: string;
	previewUrl: string;
	verses: Sutra[];
}
