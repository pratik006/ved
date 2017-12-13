import { Sutra } from './sutra';
export class Chapter {
  id: number;
  content: string;
  headline: string;
  landCode: string;
  name: string;
  sutras: Sutra[];
}